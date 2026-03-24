package com.example.navegacaoentretelas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navegacaoentretelas.Screens.LoginScreen
import com.example.navegacaoentretelas.Screens.MenuScreen
import com.example.navegacaoentretelas.Screens.PedidosScreen
import com.example.navegacaoentretelas.Screens.PerfilScreen
import com.example.navegacaoentretelas.ui.theme.NavegacaoEntreTelasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacaoEntreTelasTheme {  }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "login",
                    ) {
                        composable(route = "login") {
                            LoginScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                        composable(route = "menu") {
                            MenuScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                        composable(
                            route = "pedidos?cliente={cliente}",
                            arguments = listOf(navArgument("cliente") {
                                defaultValue = "Cliente Genérico"
                            })
                        ) {
                            PedidosScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController,
                                it.arguments?.getString("cliente")
                            )
                        }

                            composable(
                                route = "perfil/{nome}/{idade}",
                                arguments = listOf(
                                    navArgument("nome") { type = NavType.StringType },
                                    navArgument("idade") { type = NavType.IntType }
                                )
                            ) {
                                val nome: String? = it.arguments?.getString("nome", "Usuário Genérico")
                                val idade: Int? = it.arguments?.getInt("idade", 0)
                                PerfilScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    navController,
                                    nome!!,
                                    idade!!
                                )
                            }
                        }
                    }
                }
            }
        }

