package com.example.stock.feature

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppDispatchers(
  val io: CoroutineDispatcher = Dispatchers.IO,
  val main: CoroutineDispatcher = Dispatchers.Main,
  val default: CoroutineDispatcher = Dispatchers.Default
)