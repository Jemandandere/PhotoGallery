package com.jemandandere.photogallery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Отрисовка и связывание
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Получение элемента навигации
        navigationController = findNavController(R.id.navigation_controller)
        // Настройка табов
        val bottomNavigationView = binding.bottomNavigationView
        setupWithNavController(bottomNavigationView, navigationController)
        // Настройка тулбара
        setSupportActionBar(binding.toolbar)
        // Конфигурирование верхних уровней навигации и связывание всего вместе
        val appBarConfiguration = AppBarConfiguration(bottomNavigationView.menu)
        setupActionBarWithNavController(navigationController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Обеспечение корректной работоспособности кнопки back в тулбаре
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }
}
