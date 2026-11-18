package com.example.supabasesdk.data


import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class SupabaseManager {
    val client = createSupabaseClient(
        supabaseUrl = "",
        supabaseKey = ""
    ) {
        install(Postgrest)
    }
}
