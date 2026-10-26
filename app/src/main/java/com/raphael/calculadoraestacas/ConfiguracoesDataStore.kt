package com.raphael.calculadoraestacas

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.configuracoesStore: DataStore<Preferences> by preferencesDataStore(
    name = "configuracoes"
)

data class Configuracoes(
    val metrosPorEstaca: Double = 20.0,
    val permitirResultadoNegativo: Boolean = false
)

class ConfiguracoesDataStore(context: Context) {
    private val applicationContext = context.applicationContext

    private object Chaves {
        val metrosPorEstaca = doublePreferencesKey("metros_por_estaca")
        val permitirResultadoNegativo = booleanPreferencesKey("permitir_resultado_negativo")
    }

    val dados: Flow<Configuracoes> = applicationContext.configuracoesStore.data
        .map { preferencias ->
            Configuracoes(
                metrosPorEstaca = preferencias[Chaves.metrosPorEstaca] ?: 20.0,
                permitirResultadoNegativo = preferencias[Chaves.permitirResultadoNegativo] ?: false
            )
        }

    suspend fun salvar(configuracoes: Configuracoes) {
        applicationContext.configuracoesStore.edit { preferencias ->
            preferencias[Chaves.metrosPorEstaca] = configuracoes.metrosPorEstaca
            preferencias[Chaves.permitirResultadoNegativo] = configuracoes.permitirResultadoNegativo
        }
    }
}
