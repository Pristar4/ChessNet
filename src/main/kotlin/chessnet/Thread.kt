package chessnet

import java.util.concurrent.atomic.AtomicBoolean

class Thread {
    val Threads: ThreadPool = ThreadPool()


    fun startThinking(
        pos: Position,
        states: List<String>,
        limits: Any,
        ponderMode: Boolean = false
    ) {
        //TODO: implement

    }
}

class ThreadPool {
    fun startThinking(
        pos: Position,
        states: ArrayDeque<StateInfo>,
        limits: Any, // TODO: ADD SEARCH LIMITS
        ponderMode: Boolean = false
    ) {
    }

    fun clear() {}
    fun set(size: Int) {}

    //MainThread
    fun main(): Thread {
        return Thread()
    }


fun nodesSearched(): ULong {
    return 0UL
}

fun getBestThread(): Thread {
    return Thread()
}

fun startSearching() {}
fun waitForSearchFinished() {}
var stop: AtomicBoolean = AtomicBoolean(false)
var increaseDepth: AtomicBoolean = AtomicBoolean(false)
val setupStates: ArrayDeque<StateInfo> = ArrayDeque<StateInfo>()

//uint64_t accumulate(std::atomic<uint64_t> Thread::* member) const {}in kotlin





}
