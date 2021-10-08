package com.tobianoapps.shibeapi.list.repository

import com.tobianoapps.shibeapi.util.Resource


interface ShibeRepository {

    suspend fun getShibes(): Resource<List<String>?>
}
