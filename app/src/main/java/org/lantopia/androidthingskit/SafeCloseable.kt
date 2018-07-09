package org.lantopia.androidthingskit

import java.io.Closeable

interface SafeCloseable : Closeable {
    override fun close()
}
