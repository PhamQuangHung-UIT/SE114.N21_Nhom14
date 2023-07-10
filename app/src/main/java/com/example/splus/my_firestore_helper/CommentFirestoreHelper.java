package com.example.splus.my_firestore_helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.splus.my_data.Comment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommentFirestoreHelper {

    public static final int LIKE = 1;
    public static final int DISLIKE = -1;
    public static final int NULL = 0;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private static CommentFirestoreHelper instance;

    private FireStoreEventListener
            onGetCommentListListener,
            onGetCommentDataListener,
            onAddNewCommentListener,
            onDeleteListener,
            onEditListener,
            onLikeChangeListener,
            onDislikeChangeListener;

    public static CommentFirestoreHelper getInstance() {
        if (instance == null)
            instance = new CommentFirestoreHelper();
        return instance;
    }

    public List<Comment> getListComment(String courseID, String parentCommentID) {
        onGetCommentListListener.onStart();
        List<Comment> commentList = new ArrayList<>();
        Query query = db.collection("comment")
                .whereEqualTo("courseId", courseID)
                .whereEqualTo("parentCommentId", parentCommentID)
                .orderBy("createdDate", Query.Direction.DESCENDING);
        query.get().addOnCompleteListener(commentTask -> {
            if (commentTask.isSuccessful()) {
                for (QueryDocumentSnapshot document : commentTask.getResult()) {
                    Comment comment = document.toObject(Comment.class);
                    commentList.add(comment);
                }
            }
            onGetCommentListListener.onComplete(commentTask.isSuccessful(), commentTask.getException());
        }).continueWithTask(task -> {
            // Create a query to "commentReact" collection
            return db.collection("commentReact")
                    .whereEqualTo("courseId", courseID)
                    .whereEqualTo("userId", user.getUid())
                    .get();
        }).addOnCompleteListener(commentReactTask -> {
            if (commentReactTask.isSuccessful()) {
                for (QueryDocumentSnapshot document : commentReactTask.getResult()) {
                    Optional<Comment> result = commentList.stream().findFirst()
                            .filter(cmt -> cmt.getId().equals(document.get("commentId", String.class)));
                    if (result.isPresent()) {
                        Comment comment = result.get();
                        comment.setLike("like".equals(document.get("react", String.class)));
                    }
                }
            }
            onGetCommentListListener.onComplete(commentReactTask.isSuccessful(), commentReactTask.getException());
        });
        return commentList;
    }

    public Comment getComment(String commentId) {
        // Create an atomic result by creating a 1-element array
        Comment[] result = new Comment[1];
        synchronized (result) {
            db.collection("comment").document(commentId).get().addOnCompleteListener(task -> {
                if (task.isComplete())
                    result[0] = task.getResult().toObject(Comment.class);
                onGetCommentDataListener.onComplete(task.isSuccessful(), task.getException());
                synchronized (result) {
                    result.notify();
                }
            });
            onGetCommentDataListener.onStart();
            try {
                result.wait();
            } catch (InterruptedException e) {
                Log.e("Error", "getComment", e);
            }
            return result[0];
        }
    }

    public void addNewComment(String courseId, String parentCommentId, String replyCommentId, String text) {
        onAddNewCommentListener.onStart();
        Comment comment = new Comment();
        comment.setCourseId(courseId);
        comment.setParentCommentId(parentCommentId);
        comment.setReplyCommentId(replyCommentId);
        comment.setText(text);
        db.collection("comment").add(comment).addOnCompleteListener(result ->
                onAddNewCommentListener.onComplete(result.isSuccessful(), result.getException()));
    }


    public void edit(String editCommentID, String text) {
        onEditListener.onStart();
        db.collection("comment").document(editCommentID).update("text", text)
                .addOnCompleteListener(task -> onEditListener.onComplete(task.isSuccessful(), task.getException()));
    }

    // Note: Only delete comments and replies. For reacts (like, dislike),
    // we start a service which find and delete user reacts to that delete
    // comment after the user has login
    public void delete(Comment comment) {
        onDeleteListener.onStart();
        db.collection("comment").whereEqualTo("parentCommentId", comment.getId()).get()
                .continueWithTask(task -> {
                    WriteBatch batch = db.batch();
                    task.getResult().forEach(queryDocumentSnapshot -> batch.delete(queryDocumentSnapshot.getReference()));
                    batch.delete(db.collection("comment").document(comment.getId()));
                    return batch.commit();
                }).addOnCompleteListener(task -> onDeleteListener.onComplete(task.isSuccessful(), task.getException()));
    }


    public void likeChange(Comment comment, int state, int previousState) {
        db.runTransaction(transaction -> {
            try {
                DocumentReference ref = db.collection("comment").document(comment.getId());
                int likeCount = transaction.get(ref).get("likeCount", Integer.class);
                if (state == NULL)
                    likeCount++;
                else likeCount--;
                if (previousState == DISLIKE) {
                    int dislikeCount = transaction.get(ref).get("likeCount", Integer.class);
                    transaction.update(ref, "dislikeCount", --dislikeCount);
                }
                transaction.update(ref, "likeCount", likeCount);
            } catch (NullPointerException npe) {
                throw new FirebaseFirestoreException("Unexisted comment", FirebaseFirestoreException.Code.NOT_FOUND, npe);
            }
            return null;
        }).continueWithTask(
                task -> db.collection("commentReact")
                                .whereEqualTo("commentId", comment.getId())
                                .whereEqualTo("userId", user.getUid())
                                .get()
        ).continueWithTask(task -> {
            DocumentReference ref;
            if (task.getResult().size() != 0) {
                ref = task.getResult().getDocuments().get(0).getReference();
                if (state == NULL)
                    return ref.delete();
                else return ref.update("react","like");
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("commentId", comment.getId());
                map.put("userId", user.getUid());
                map.put("react", "like");
                ref = db.collection("commentReact").document();
                return ref.set(map);
            }
        }).addOnCompleteListener(task -> onLikeChangeListener.onComplete(task.isSuccessful(), task.getException()));
    }

    public void dislikeChange(Comment comment, int state, int previousState) {
        db.runTransaction(transaction -> {
            try {
                DocumentReference ref = db.collection("comment").document(comment.getId());
                int likeCount = transaction.get(ref).get("dislikeCount", Integer.class);
                if (state == DISLIKE)
                    likeCount++;
                else likeCount--;
                if (previousState == LIKE) {
                    int dislikeCount = transaction.get(ref).get("likeCount", Integer.class);
                    transaction.update(ref, "dislikeCount", --dislikeCount);
                }
                transaction.update(ref, "dislikeCount", likeCount);
            } catch (NullPointerException npe) {
                throw new FirebaseFirestoreException("Unexisted comment", FirebaseFirestoreException.Code.NOT_FOUND, npe);
            }
            return null;
        }).continueWithTask(
                task -> db.collection("commentReact")
                        .whereEqualTo("commentId", comment.getId())
                        .whereEqualTo("userId", user.getUid())
                        .get()
        ).continueWithTask(task -> {
            DocumentReference ref;
            if (task.getResult().size() != 0) {
                ref = task.getResult().getDocuments().get(0).getReference();
                if (state == NULL)
                    return ref.delete();
                else return ref.update("react","dislike");
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("commentId", comment.getId());
                map.put("userId", user.getUid());
                map.put("react", "dislike");
                ref = db.collection("commentReact").document();
                return ref.set(map);
            }
        }).addOnCompleteListener(task -> onDislikeChangeListener.onComplete(task.isSuccessful(), task.getException()));
    }

    public void setOnGetCommentListListener(FireStoreEventListener onGetCommentListListener) {
        this.onGetCommentListListener = onGetCommentListListener;
    }

    public void setOnGetCommentDataListener(FireStoreEventListener onGetCommentDataListener) {
        this.onGetCommentDataListener = onGetCommentDataListener;
    }

    public void setOnAddNewCommentListener(FireStoreEventListener onAddNewCommentListener) {
        this.onAddNewCommentListener = onAddNewCommentListener;
    }

    public void setOnDeleteListener(FireStoreEventListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public void setOnEditListener(FireStoreEventListener onEditListener) {
        this.onEditListener = onEditListener;
    }

    public void setOnLikeChangeListener(FireStoreEventListener onLikeChangeListener) {
        this.onLikeChangeListener = onLikeChangeListener;
    }

    public void setOnDislikeChangeListener(FireStoreEventListener onDislikeChangeListener) {
        this.onDislikeChangeListener = onDislikeChangeListener;
    }

    public interface FireStoreEventListener {
        void onStart();
        void onComplete(boolean isSuccessful, Exception exception);
    }
}
