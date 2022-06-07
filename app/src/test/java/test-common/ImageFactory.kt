package `test-common`

import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.model.ImageUrls
import java.util.concurrent.atomic.AtomicInteger

class ImageFactory {
    private val counter = AtomicInteger(0)
    fun createImageResponse(): ImageResponse {
        val id = counter.incrementAndGet()

        return ImageResponse(
            color = "#60544D",
            height = 3497,
            width = 5245,
            id = id.toString(),
            imageUrls = ImageUrls(
                full = "https://images.unsplash.com/photo-1648737966968-5f50e6bf9e46?crop=entropy&cs=tinysrgb&fm=jpg&ixid=MnwxOTAzOTR8MXwxfGFsbHwzMDF8fHx8fHwzfHwxNjU0NTgzNzgy&ixlib=rb-1.2.1&q=80",
                regular = "https://images.unsplash.com/photo-1648737966968-5f50e6bf9e46?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxOTAzOTR8MXwxfGFsbHwzMDF8fHx8fHwzfHwxNjU0NTgzNzgy&ixlib=rb-1.2.1&q=80&w=1080",
                small = "https://images.unsplash.com/photo-1648737966968-5f50e6bf9e46?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxOTAzOTR8MXwxfGFsbHwzMDF8fHx8fHwzfHwxNjU0NTgzNzgy&ixlib=rb-1.2.1&q=80&w=400",
                thumb = "https://images.unsplash.com/photo-1648737966968-5f50e6bf9e46?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxOTAzOTR8MXwxfGFsbHwzMDF8fHx8fHwzfHwxNjU0NTgzNzgy&ixlib=rb-1.2.1&q=80&w=200"
            )
        )
    }
}
