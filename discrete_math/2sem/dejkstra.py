inf = float('inf')
weightedGraph = [
[0,inf,	4, 3, inf, inf, 2, 2, 2, 4, inf, 3],        # 1
[inf, 0, inf, inf, inf, inf, 1, 4, inf, 5, 1, inf], # 2
[4, inf, 0, inf, 3, 2, inf, inf, 1, 4, 4, 3],       # 3
[3, inf, inf, 0, inf, 3, 5, inf, 2, inf, 3, 4],     # 4
[inf, inf, 3, inf, 0, 1, 4, 5, 3, inf, 1, 5],       # 5
[inf, inf, 2, 3, 1, 0, 2, 2, inf, inf, inf, inf],   # 6
[2, 1, inf, 5, 4, 2, 0, 1, inf, 2, inf, 3],         # 7
[2, 4, inf, inf, 5, 2, 1, 0, inf, inf, 3, 5],       # 8
[2, inf, 1, 2, 3, inf, inf, inf, 0, inf, 1, inf],   # 9
[4, 5, 4, inf, inf, inf, 2, inf, inf, 0, 1, 1],     # 10
[inf, 1, 4, 3, 1, inf, inf, 3, 1, 1, 0, inf],       # 11
[3, inf, 3, 4, 5, inf, 3, 5, inf, 1, inf, 0]        # 12
]
cur_min_dist = [0,inf, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf]

unvisited = set(i for i in range(len(weightedGraph)))

p = 0
while unvisited:
    for i in range(1, len(cur_min_dist)):
        cur_min_dist[i] = min(cur_min_dist[i], weightedGraph[p][i] + cur_min_dist[p])
    unvisited.remove(p)

    cm = inf
    cp = 0
    for e in unvisited:
        if cur_min_dist[e] < cm:
            cm = cur_min_dist[e]
            cp = e

    p = cp
    print(p+1," ", cur_min_dist[p])
print(cur_min_dist)
