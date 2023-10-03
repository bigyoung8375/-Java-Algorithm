package BOJ_17471

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter
import java.util.*
import kotlin.math.abs

// https://www.acmicpc.net/problem/17471
// input
private lateinit var br: BufferedReader

// variables
private var N = 0
private var ans = Int.MAX_VALUE
private lateinit var population: IntArray
private lateinit var adjList: MutableList<MutableList<Int>>
private lateinit var isVisited: BooleanArray

fun main() {
    val path = "C:\\Users\\bigyo\\Desktop\\알고리즘\\KotlinAlgo\\src\\main\\kotlin\\BOJ_17471\\res.txt"
    br = BufferedReader(File(path).bufferedReader())
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    input()

    bw.write(solve())
    bw.close()
} // End of main()

private fun solve(): String {
    val sb = StringBuilder()

    DFS(1, 0)

    if (ans == Int.MAX_VALUE) {
        sb.append(-1)
    } else {
        sb.append(ans)
    }

    return sb.toString()
} // End of solve()

private fun DFS(node: Int, depth: Int) {
    if (depth == N) return

    if (node == N) {
        val g1 = ArrayList<Int>()
        val g2 = ArrayList<Int>()

        for (i in 1..N) {
            if (isVisited[i]) {
                g1.add(i)
            } else {
                g2.add(i)
            }
        }


        if (g1.isEmpty() || g2.isEmpty()) return
        if (!BFS(g1) || !BFS(g2)) return

        var sum1 = 0
        var sum2 = 0
        for (num in g1) {
            sum1 += population[num]
        }

        for (num in g2) {
            sum2 += population[num]
        }

        ans = ans.coerceAtMost(abs(sum1 - sum2))
        return
    }

    isVisited[node] = true
    DFS(node + 1, depth + 1)
    isVisited[node] = false
    DFS(node + 1, depth + 1)
} // End of DFS()

private fun BFS(group: ArrayList<Int>): Boolean {
    val que: Queue<Int> = LinkedList()
    val vis = BooleanArray(N + 1)
    que.offer(group[0])
    vis[group[0]] = true

    var cnt = 1
    while (que.isNotEmpty()) {
        val nowNode = que.poll()

        for (nextNode in adjList[nowNode]) {
            if (!vis[nextNode] && isVisited[nextNode] == isVisited[nowNode]) {
                vis[nextNode] = true
                que.offer(nextNode)
                cnt++
            }
        }
    }

    return cnt == group.size
} // End of BFS

private fun input() {
    N = br.readLine().toInt()

    isVisited = BooleanArray(N + 1)
    population = IntArray(N + 1)
    StringTokenizer(br.readLine()).run {
        repeat(N) { idx ->
            population[idx + 1] = nextToken().toInt()
        }
    }

    adjList = ArrayList()
    repeat(N + 1) {
        adjList.add(ArrayList())
    }

    repeat(N) { idx ->
        StringTokenizer(br.readLine()).run {
            val temp = nextToken().toInt()

            repeat(temp) {
                val u = nextToken().toInt()
                adjList[idx + 1].add(u)
            }
        }
    }
} // End of input()
