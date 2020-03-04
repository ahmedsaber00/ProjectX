package io.android.projectx.presentation.base.state

data class Resource<out T> constructor(
    val status: ResourceState,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceState.SUCCESS, data, null)
        }
        fun <T> error(msg: String): Resource<T> {
            return Resource(ResourceState.ERROR, null, msg)
        }
        fun <T> loading(): Resource<T> {
            return Resource(ResourceState.LOADING, null, null)
        }
    }
}