package com.dingshuwang.model

/**
 * Created by Steven on 2015/10/23 0023.
 */
class AccessTokenItem : APIResult() {
    var accessToken: AccessToken? = null

    class AccessToken : BaseItem() {
        var accessToken: String? = null
        var refreshToken: String? = null
        var expiresIn: String? = null
        var tokenType: String? = null
        var uid: String? = null
    }

}
