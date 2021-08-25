package com.pow.services

import com.pow.models.Method
import com.pow.models.Request
import com.pow.repositories.RequestRepository
import com.pow.serializers.RequestDTO
import java.util.*
import javax.inject.Inject

class RequestService {

    @Inject lateinit var requestRepository: RequestRepository

    fun findById(id: UUID): Optional<Request> = requestRepository.findById(id)

    fun deleteIdBy(request: Request) = requestRepository.deleteIdBy(request)

    fun getRequestList(requests: List<Request>): MutableList<RequestDTO> {
        val requestsDTO: MutableList<RequestDTO> = mutableListOf()
        requests.forEach {
            requestsDTO.add(RequestDTO(it.id, it.path, it.method, it.workspace.id))
        }
        return requestsDTO
    }

    fun save(request: Request): Request? {
        requestRepository.findByPathAndMethodAndWorkspace(request.path, request.method, request.workspace)
            ?: return requestRepository.save(request)
        return null
    }

    fun update(request: Request, path: String, method: Method): Request? {
        return if (requestRepository.findByPathAndMethodAndWorkspace(path, method, request.workspace) == null) {
            request.path = path
            request.method = method
            request
        } else {
            null
        }
    }
}