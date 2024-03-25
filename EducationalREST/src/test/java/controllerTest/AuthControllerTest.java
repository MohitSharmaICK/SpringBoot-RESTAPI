package controllerTest;


import com.example.EducationalREST.EducationalRestApplication;
import com.example.EducationalREST.controller.AuthController;
import com.example.EducationalREST.dto.ReqRes;
import com.example.EducationalREST.repository.OurUserRepo;
import com.example.EducationalREST.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EducationalRestApplication.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

//    @Mock
//    private OurUserRepo ourUserRepo;

    @Mock
    private AuthService authService;

    //MethodName_StateUnderTest_ExpectedBehaviour

    //1.Testing for validSignUp and Invalid signUp Both

    @Test
    public void signUp_validSignUp_returnSuccess(){
        // Mock input and output
        ReqRes signUpRequest = new ReqRes();
        ReqRes expectedResponse = new ReqRes();

        // Mock AuthService behavior
        when(authService.signUp(signUpRequest)).thenReturn(expectedResponse);

        // Call the API
        ResponseEntity<ReqRes> response = authController.signUp(signUpRequest);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        // Verify that the signUp method of AuthService is called once
        verify(authService, times(1)).signUp(signUpRequest);
    }

    @Test
    public void signUp_invalidSignUp_throwsException(){
        // Mock invalid sign-up request
        ReqRes invalidSignUpRequest = new ReqRes();
        // Mock AuthService behavior to throw an exception
        when(authService.signUp(invalidSignUpRequest)).thenThrow(new RuntimeException("Invalid sign-up request"));

        // Execute and assert
        assertThrows(RuntimeException.class, () -> authController.signUp(invalidSignUpRequest));

        // Verify that the signUp method of AuthService is called once with the specified argument
        verify(authService, times(1)).signUp(invalidSignUpRequest);
    }


    //2.Testing for valid SignIn and invalidSignIn both

    @Test
    public void signIn_validSignIn_returnSuccess(){
        // Mock valid sign-in request
        ReqRes validSignInRequest = new ReqRes();
        ReqRes expectedResponse = new ReqRes();

        // Mock AuthService behavior to return success
        when(authService.signIn(validSignInRequest)).thenReturn(expectedResponse);

        // Execute and assert
        ResponseEntity<ReqRes> response = authController.signIn(validSignInRequest);
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void signIn_invalidSignIn_throwsException(){
        // Mock invalid sign-in request
        ReqRes invalidSignInRequest = new ReqRes();

        // Mock AuthService behavior to throw an exception
        when(authService.signIn(invalidSignInRequest)).thenThrow(new RuntimeException("Invalid sign-in request"));

        // Execute and assert
        assertThrows(RuntimeException.class, () -> authController.signIn(invalidSignInRequest));
    }


    //3.Testing for valid refreshToken and invalid refresh token

    @Test
    public void refreshToken_validToken_returnSuccess(){
        // Mock valid token refresh request
        ReqRes validTokenRefreshRequest = new ReqRes();
        ReqRes expectedResponse = new ReqRes();

        // Mock AuthService behavior to return success
        when(authService.refreshToken(validTokenRefreshRequest)).thenReturn(expectedResponse);

        // Execute and assert
        ResponseEntity<ReqRes> response = authController.refreshToken(validTokenRefreshRequest);
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void refreshToken_invalidToken_throwsException(){
        // Mock invalid token refresh request
        ReqRes invalidTokenRefreshRequest = new ReqRes();

        // Mock AuthService behavior to throw an exception
        when(authService.refreshToken(invalidTokenRefreshRequest)).thenThrow(new RuntimeException("Invalid token refresh request"));

        // Execute and assert
        assertThrows(RuntimeException.class, () -> authController.refreshToken(invalidTokenRefreshRequest));
    }

}
