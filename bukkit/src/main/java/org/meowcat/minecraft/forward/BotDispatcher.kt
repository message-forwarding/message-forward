package org.meowcat.minecraft.forward

import net.mamoe.mirai.Bot
import net.mamoe.mirai.containsGroup

/**
 * 一个bot调度器
 * 决定哪个监听监听
 * 哪些bot进行发送
 * 还负责校检合法性
 */
class BotDispatcher private constructor() {
    companion object{
        fun create():BotDispatcher{
            return BotDispatcher()
        }
    }

    val allBots = HashSet<Bot>()
    val speakers = HashSet<Bot>()
    var listener = 12345678L
    var target = 12345678L

    fun reDispatch(){
        changeTarget(this.target)
    }

    fun addBot(bot: Bot):BotDispatcher{
        allBots.add(bot)
        return this
    }

    fun changeTarget(target:Long){
        this.target = target
        if (allBots.isEmpty()) return
        //清除speaker
        speakers.clear()
        allBots.forEach {
            it.containsGroup(target)
            speakers.add(it)
        }
        var listener = speakers.random().id
    }

    fun findBotByID(id:Long):Bot?{
        allBots.forEach {
            if (it.id == id) return it
        }
        return null
    }
}