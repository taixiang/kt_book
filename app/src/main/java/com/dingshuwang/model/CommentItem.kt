package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by tx on 2017/6/16.
 */

class CommentItem : Serializable {
    var message: String? = null
    var result: Boolean = false
    var Comment: List<Comment1>? = null

    inner class Comment1 : Serializable {
        var comment_content: String? = null
        var add_time: String? = null
        var innerid: String? = null
    }
}
