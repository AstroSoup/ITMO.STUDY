import copy
tensor_A = [[[5, -6, -4], [1, 5, 2], [0, 0, 0]],
            [[3, -3, 5], [5, 1, 0], [5, -3, -2]],
            [[-1, -6, 6], [0, -3, -3], [-2, -4, 3]]]
tensor_B = [[-2, 4, 5], [3, 5, 3], [-5, -3, 2]]
tensor_C = [[2, -4, 4], [-3, -5, 5], [5, -1, -4]]

tensor_B_copy = tensor_B.copy()
for j in range(len(tensor_B)):
    for i in range(len(tensor_B)):
        tensor_A_copy = copy.deepcopy(tensor_A)
        mpl = tensor_B[j][i]
        for r in range(len(tensor_A_copy)):
            for p in range(len(tensor_A_copy[r])):
                for s in range(len(tensor_A_copy[r][p])):
                    tensor_A_copy[r][p][s] = mpl * tensor_A_copy[r][p][s]
        tensor_B_copy[j][i] = tensor_A_copy
print(tensor_B_copy)
print()


tensor_C_copy = copy.deepcopy(tensor_C)
for n in range(len(tensor_C_copy)):
    for l in range(len(tensor_C_copy[n])):
        tensor_B_copy_copy = copy.deepcopy(tensor_B_copy)

        for j in range(len(tensor_B_copy_copy)):
            for r in range(len(tensor_B_copy_copy[j])):
                print(tensor_B_copy_copy[j][r])
                for p in range(len(tensor_B_copy_copy[j][r])):
                    for s in range(len(tensor_B_copy_copy[j][r][p])):
                        for k in range(len(tensor_B_copy_copy[j][r][p][s])):
                            tensor_B_copy_copy[j][r][p][s][k] *= tensor_C[n][l]
            tensor_C_copy[n][l] = tensor_B_copy_copy
print(tensor_C_copy)
print()
result = [0, 0, 0]

for l in range(len(tensor_C_copy)):
    for t in range(len(tensor_C_copy[l])):
        for m in range(len(tensor_C_copy[l][t])):
            for k in range(len(tensor_C_copy[l][m][t])):
                result[l] += tensor_C_copy[l][m][t][k][t][m][k]
print(result)
