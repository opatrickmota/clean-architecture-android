package com.patrickmota.cleanarchitecture.framework

import android.content.Context
import com.patrickmota.cleanarchitecture.framework.db.BookmarkEntity
import com.patrickmota.cleanarchitecture.framework.db.MajesticReaderDatabase
import com.patrickmota.core.data.BookmarkDataSource
import com.patrickmota.core.domain.Bookmark
import com.patrickmota.core.domain.Document

class RoomBookmarkDataSource(context: Context) : BookmarkDataSource {

    // 1
    private val bookmarkDao = MajesticReaderDatabase.getInstance(context).bookmarkDao()

    // 2
    override suspend fun add(document: Document, bookmark: Bookmark) =
        bookmarkDao.addBookmark(
            BookmarkEntity(
            documentUri = document.url,
            page = bookmark.page
        )
        )

    override suspend fun read(document: Document): List<Bookmark> = bookmarkDao
        .getBookmarks(document.url).map { Bookmark(it.id, it.page) }

    override suspend fun remove(document: Document, bookmark: Bookmark) =
        bookmarkDao.removeBookmark(
            BookmarkEntity(id = bookmark.id, documentUri = document.url, page = bookmark.page)
        )
}