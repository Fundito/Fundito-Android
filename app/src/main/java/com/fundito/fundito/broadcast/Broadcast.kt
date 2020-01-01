package com.fundito.fundito.broadcast

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

/**
 * Created by mj on 01, January, 2020
 */
@ExperimentalCoroutinesApi
object Broadcast {
    val chargeCompleteEvent : BroadcastChannel<Int> = ConflatedBroadcastChannel()

    val fundEvent : BroadcastChannel<Pair<Int,Int>> = ConflatedBroadcastChannel()
}