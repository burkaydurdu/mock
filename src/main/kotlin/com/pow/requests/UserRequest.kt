package com.pow.requests

data class SignUpRequest(val name: String, val email: String, val password: String)
data class SingInRequest(val email: String, val password: String)
data class UserUpdateRequest(val name: String)
data class SendResetPasswordRequest(val email: String)
data class ResetPasswordRequest(val password: String)