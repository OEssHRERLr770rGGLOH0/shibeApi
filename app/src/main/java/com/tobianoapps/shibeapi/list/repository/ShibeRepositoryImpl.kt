package com.tobianoapps.shibeapi.list.repository

import com.tobianoapps.shibeapi.list.api.ShibeApi
import com.tobianoapps.shibeapi.util.Resource
import java.io.IOException

class ShibeRepositoryImpl : ShibeRepository {

    /**
     * Use this to call the [ShibeApi]
     *
     * @return A list of image string urls wrapped in a [Resource] class.
     */
    override suspend fun getShibes(): Resource<List<String>?> {
        return try {
            Resource.Success(ShibeApi().getShibes())
        } catch (e: IOException) {
            Resource.Error("Error when getting shibes: $e")
        }
    }
}
