package kz.sdk.shina.models

data class User(
    var name: String? = null,
    var lastname: String?= null,
    var address:String? = null,
    var pictureUrl: String? = null,
    var cart: Map<String, Product> = emptyMap(),
    var bonus:Float? = 0.0f,
)
