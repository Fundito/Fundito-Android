package com.fundito.fundito.broadcast

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel

/**
 * Created by mj on 01, January, 2020
 */
object Broadcast {
    val chargeCompleteEvent : BroadcastChannel<Int> = BroadcastChannel(Channel.CONFLATED)

    val fundEvent : BroadcastChannel<Pair<Int,Int>> = BroadcastChannel(Channel.CONFLATED)
}