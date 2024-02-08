import android.hardware.camera2.CameraManager
import com.example.open_camera.MainActivity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {

    @Mock
    lateinit var mockCameraManager: CameraManager

    private lateinit var mainActivity: MainActivity

    @Before
    fun setUp() {
        mainActivity = MainActivity()
        mainActivity.cameraManager = mockCameraManager
        mainActivity.getCameraID = "cameraId" // Assuming cameraId is set
    }

    @Test
    fun `test toggling flashlight on`() {
        // Given
        mainActivity.flashFlag = false
        `when`(mockCameraManager.cameraIdList).thenReturn(arrayOf("cameraId"))

        // When
        mainActivity.toggle()

        // Then
        assertTrue(mainActivity.flashFlag)
        verify(mockCameraManager).setTorchMode("cameraId", true)
    }

    @Test
    fun `test toggling flashlight off`() {
        // Given
        mainActivity.flashFlag = true
        `when`(mockCameraManager.cameraIdList).thenReturn(arrayOf("cameraId"))

        // When
        mainActivity.toggle()

        // Then
        assertFalse(mainActivity.flashFlag)
        verify(mockCameraManager).setTorchMode("cameraId", false)
    }

    @Test
    fun `test toggling flashlight toggles flag correctly`() {
        // Given
        mainActivity.flashFlag = false
        `when`(mockCameraManager.cameraIdList).thenReturn(arrayOf("cameraId"))

        // When
        mainActivity.toggle()

        // Then
        assertTrue(mainActivity.flashFlag)

        // When toggling again
        mainActivity.toggle()

        // Then
        assertFalse(mainActivity.flashFlag)
    }
}
