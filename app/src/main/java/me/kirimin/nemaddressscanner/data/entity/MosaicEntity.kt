package me.kirimin.nemaddressscanner.data.entity

class MosaicEntity {

    lateinit var quantity: String
    lateinit var mosaicId: MosaicIdEntity

    val displayName get() = "${mosaicId.namespaceId}:${mosaicId.name}"

    class MosaicIdEntity {

        lateinit var namespaceId: String
        lateinit var name: String

    }
}