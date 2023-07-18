package com.example.splus.my_class;

import android.graphics.Color;
import android.os.Parcel;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;

public class ReplyToOwnerSpan extends ForegroundColorSpan {

    String replyOwnerId;

    public ReplyToOwnerSpan(String replyOwnerId) {
        super(Color.BLUE);
        this.replyOwnerId = replyOwnerId;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(replyOwnerId);
        super.writeToParcel(dest, flags);
    }

    public String getReplyOwnerId() {
        return replyOwnerId;
    }
}
