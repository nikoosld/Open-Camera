import android.hardware.camera2.CameraManager
import com.example.open_camera.HomeScreen
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
class HomeScreenTest {

    @Mock
    lateinit var mockCameraManager: CameraManager

    private lateinit var HomeScreen: HomeScreen

    @Before
    fun setUp() {
        HomeScreen = HomeScreen()
        HomeScreen.cameraManager = mockCameraManager
        HomeScreen.getCameraID = "cameraId" // Assuming cameraId is set
    }

    @Test
    fun `test toggling flashlight on`() {
        // Given
        HomeScreen.flashFlag = false
        `when`(mockCameraManager.cameraIdList).thenReturn(arrayOf("cameraId"))

        // When
        HomeScreen.toggle()

        // Then
        assertTrue(HomeScreen.flashFlag)
        verify(mockCameraManager).setTorchMode("cameraId", true)
    }

    @Test
    fun `test toggling flashlight off`() {
        // Given
        HomeScreen.flashFlag = true
        `when`(mockCameraManager.cameraIdList).thenReturn(arrayOf("cameraId"))

        // When
        HomeScreen.toggle()

        // Then
        assertFalse(HomeScreen.flashFlag)
        verify(mockCameraManager).setTorchMode("cameraId", false)
    }

    @Test
    fun `test toggling flashlight toggles flag correctly`() {
        // Given
        HomeScreen.flashFlag = false
        `when`(mockCameraManager.cameraIdList).thenReturn(arrayOf("cameraId"))

        // When
        HomeScreen.toggle()

        // Then
        assertTrue(HomeScreen.flashFlag)

        // When toggling again
        HomeScreen.toggle()

        // Then
        assertFalse(HomeScreen.flashFlag)
    }
}
