package io.litecodez.typetrain

/**
 * Executes the given block after any operation
 * Useful for chaining operations where you want to perform an action regardless of the result
 */
inline fun Unit.then(block: Unit.() -> Unit) {
    val result = this
    result.block()
}

/**
 * Executes the given block if the receiver is not null
 * Returns the receiver to allow chaining
 */
inline fun <T> T?.ifNotNull(block: (T) -> Unit): T? {
    if (this != null) {
        block(this)
    }
    return this
}

/**
 * Executes the given block if the receiver is not empty
 * Supports Collections, Maps, CharSequences, Arrays, and custom isEmpty implementations
 * Returns the receiver to allow chaining
 */
inline fun <T> T?.ifNotEmpty(block: (T) -> Unit): T? {
    when (this) {
        null -> return null
        is String -> if(isNotEmpty()) block(this)
        is Collection<*> -> if (isNotEmpty()) block(this)
        is Map<*, *> -> if (isNotEmpty()) block(this)
        is CharSequence -> if (isNotEmpty()) block(this)
        is Array<*> -> if (isNotEmpty()) block(this)
    }
    return this
}

/**
 * Executes the given block if the receiver is empty
 * Supports Collections, Maps, CharSequences, Arrays, and custom isEmpty implementations
 * Returns the receiver to allow chaining
 */
inline fun <T> T?.ifEmpty(block: (T) -> Unit): T? {
    when (this) {
        null -> return null
        is String -> if(isEmpty()) block(this)
        is Collection<*> -> if (isEmpty()) block(this)
        is Map<*, *> -> if (isEmpty()) block(this)
        is CharSequence -> if (isEmpty()) block(this)
        is Array<*> -> if (isEmpty()) block(this)
    }
    return this
}

/**
 * Executes the given block if the receiver is null
 * Returns the receiver to allow chaining
 */
inline fun <T> T?.ifNull(block: () -> Unit): T? {
    if (this == null) {
        block()
    }
    return this
}

/**
 * Executes success block if receiver is not null, failure block if null
 * Returns the receiver to allow chaining
 */
inline fun <T> T?.ifNullOrNot(
    successBlock: (T) -> Unit,
    failureBlock: () -> Unit
): T? {
    if (this != null) {
        successBlock(this)
    } else {
        failureBlock()
    }
    return this
}