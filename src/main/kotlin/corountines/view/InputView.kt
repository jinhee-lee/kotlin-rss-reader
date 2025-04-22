package corountines.view

import kotlinx.coroutines.delay

object InputView {
    suspend fun readSearchKeyword(): String {
        println("검색어를 입력하세요 (없으면 전체 출력)")
        return readlnOrNull() ?: ""
    }
}
