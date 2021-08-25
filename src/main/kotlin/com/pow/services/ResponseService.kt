package com.pow.services

import com.pow.models.MimeType
import com.pow.models.Response
import com.pow.repositories.ResponseRepository
import com.pow.serializers.ResponseDTO
import java.util.*
import javax.inject.Inject

class ResponseService {
    @Inject lateinit var responseRepository: ResponseRepository

    fun findById(id: UUID): Optional<Response> = responseRepository.findById(id)

    fun deleteIdBy(response: Response) = responseRepository.deleteIdBy(response)

    fun getResponseList(response: List<Response>): MutableList<ResponseDTO> {
        val responsesDTO: MutableList<ResponseDTO> = mutableListOf()
        response.forEach {
            responsesDTO.add(ResponseDTO(it.id, it.headers, it.body, it.mimeType, it.code))
        }
        return  responsesDTO
    }

    fun save(response: Response): Response? {
        responseRepository.findByCodeAndRequest(response.code, response.request)
            ?: return responseRepository.save(response)
        return null
    }

    fun update(response: Response, headers: Map<String, Any>, body: String?, mimeType: MimeType, code: Int): Response? {
        if (response.code != code && responseRepository.findByCodeAndRequest(code, response.request) != null) {
            return null
        }

        response.headers = headers
        response.body = body
        response.mimeType = mimeType
        response.code = code
        return response
    }
}