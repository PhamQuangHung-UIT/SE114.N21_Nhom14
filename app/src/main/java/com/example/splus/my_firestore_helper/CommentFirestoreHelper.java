package com.example.splus.my_firestore_helper;

import androidx.annotation.NonNull;

import com.example.splus.my_data.Comment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CommentFirestoreHelper {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private static CommentFirestoreHelper instance;

    private OnGetDataListener onGetCommentListListener, onGetReplyListListener, onGetCommentDataListener;

    public static CommentFirestoreHelper getInstance() {
        if (instance == null)
            instance = new CommentFirestoreHelper();
        return instance;
    }

    public void getListComment(String courseID, @NonNull List<Comment> commentList) {
        Query query = db.collection("comment")
                .whereEqualTo("courseID", courseID)
                .whereEqualTo("commentParentID", null);
        Lock lock = new ReentrantLock();
        Condition isCompleted = lock.newCondition();
        Task<QuerySnapshot> task = query.get();
        task.addOnCompleteListener(commentTask -> {
            lock.lock();
            if (commentTask.isSuccessful()) {
                for (QueryDocumentSnapshot document : commentTask.getResult()) {
                    Comment comment = document.toObject(Comment.class);
                    comment.setId(document.getId());
                    commentList.add(comment);
                }
                // Create a query to "commentReact" collection
                db.collection("commentReact")
                        .whereEqualTo("courseID", courseID)
                        .whereEqualTo("userID", user.getUid())
                        .get().addOnCompleteListener(commentReactTask -> {
                            if (commentReactTask.isSuccessful()) {
                                for (QueryDocumentSnapshot document : commentReactTask.getResult()) {
                                    Optional<Comment> result  = commentList.stream().findFirst()
                                            .filter(cmt -> cmt.getId().equals(document.get("commentID", String.class)));
                                    if (result.isPresent()) {
                                        Comment comment = result.get();
                                        comment.setLike("like".equals(document.get("react", String.class)));
                                    }
                                }
                            } // else if fail
                        });
                commentList.sort(Comparator.comparing(Comment::getCreatedDate).reversed());
            }
            onGetCommentListListener.onComplete(commentTask.isSuccessful(), commentTask.getException());
            isCompleted.signal();
            lock.unlock();
        });
        onGetCommentListListener.onStart();
    }

    public void getListReplies(String courseID, String parentCommentID, @NonNull List<Comment> repliesList) {
        Query query = db.collection("comment")
                .whereEqualTo("commentParentID", parentCommentID);
        Task<QuerySnapshot> task = query.get();
        task.addOnCompleteListener(commentTask -> {
            if (commentTask.isSuccessful()) {
                for (QueryDocumentSnapshot document : commentTask.getResult()) {
                    Comment comment = document.toObject(Comment.class);
                    comment.setId(document.getId());
                    repliesList.add(comment);
                }
                // Create a query to "commentReact" collection
                db.collection("commentReact")
                        .whereEqualTo("courseID", courseID)
                        .whereEqualTo("userID", user.getUid())
                        .get().addOnCompleteListener(commentReactTask -> {
                            if (commentReactTask.isSuccessful()) {
                                for (QueryDocumentSnapshot document : commentReactTask.getResult()) {
                                    Optional<Comment> result  = repliesList.stream().findFirst()
                                            .filter(cmt -> cmt.getId().equals(document.get("commentID", String.class)));
                                    if (result.isPresent()) {
                                        Comment comment = result.get();
                                        comment.setLike("like".equals(document.get("courseID", String.class)));
                                    }
                                }
                            } // else if fail
                        });
                repliesList.sort(Comparator.comparing(Comment::getCreatedDate).reversed());
            }
            onGetReplyListListener.onComplete(commentTask.isSuccessful(), commentTask.getException());
        });
        onGetReplyListListener.onStart();
    }

    public void getComment(String commentId, Comment outData) {
        db.collection("comment").document(commentId).get().addOnCompleteListener(task -> {
            if (task.isComplete())
                task.getResult().toObject(Comment.class);
            onGetCommentDataListener.onComplete(task.isSuccessful(), task.getException());
        });
        onGetCommentDataListener.onStart();
    }

    public void setOnGetCommentListListener(OnGetDataListener onGetCommentListListener) {
        this.onGetCommentListListener = onGetCommentListListener;
    }

    public void setOnGetReplyListListener(OnGetDataListener onGetReplyListListener) {
        this.onGetReplyListListener = onGetReplyListListener;
    }

    public void setOnGetCommentDataListener(OnGetDataListener onGetCommentDataListener) {
        this.onGetCommentDataListener = onGetCommentDataListener;
    }

    public interface OnGetDataListener {
        void onStart();
        void onComplete(boolean isSuccessful, Exception exception);
    }
}
