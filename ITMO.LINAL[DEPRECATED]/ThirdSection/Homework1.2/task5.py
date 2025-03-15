v1 = [1, 2, -1, -1]
v2 = [2, -4, 0, 4]
tensor_A = [[-2, 0, -6, 1], [0, 5, -6, -5], [1, -4, 1, 0], [3, -1, -5, -4]]
rst = 0
for k in range(len(tensor_A)):
    for t in range(len(tensor_A[k])):
       rst += tensor_A[k][t] * v1[k] * v2[t]
print(rst)
