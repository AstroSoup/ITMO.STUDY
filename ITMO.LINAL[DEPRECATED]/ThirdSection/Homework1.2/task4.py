import copy
tensor_A = [[[3, -4, 3], [3, 2, -3], [4, 1, 1]],
            [[4, 2, 1], [-3, 3, 4], [-4, -2, 5]],
            [[4, -1, -4], [5, 3, -2], [0, -1, -1]]]
tensor_B = [-5, -3, -1]
tensor_C = [[[3, 0, 3], [5, 3, -2], [-6, 1, -2]],
            [[-5, -4, -4], [0, -2, -5], [-3, 0, 5]],
            [[2, 0, 1], [6, 2, 3], [-1, -1, 0]]]

tensor_B_copy = tensor_B.copy()
for j in range(len(tensor_B)):
    tensor_A_copy = copy.deepcopy(tensor_A)
    mpl = tensor_B[j]
    for r in range(len(tensor_A_copy)):
        for p in range(len(tensor_A_copy[r])):
            for s in range(len(tensor_A_copy[r][p])):
                tensor_A_copy[r][p][s] = mpl * tensor_A_copy[r][p][s]
    tensor_B_copy[j] = tensor_A_copy
print(tensor_B_copy)
print()


tensor_C_copy = copy.deepcopy(tensor_C)
for n in range(len(tensor_C_copy)):
    for l in range(len(tensor_C_copy[n])):
        for k in range(len(tensor_C_copy[n][l])):
            tensor_B_copy_copy = copy.deepcopy(tensor_B_copy)
            #print(tensor_B_copy_copy)
            for j in range(len(tensor_B_copy_copy)):

                for r in range(len(tensor_B_copy_copy[j])):
                    for p in range(len(tensor_B_copy_copy[j][r])):
                        for s in range(len(tensor_B_copy_copy[j][r][p])):
                            tensor_B_copy_copy[j][r][p][s] *= tensor_C[n][l][k]
            tensor_C_copy[n][l][k] = tensor_B_copy_copy
print(tensor_C_copy)
print()
result = [0, 0, 0]

for n in range(len(tensor_C_copy)):
    for l in range(len(tensor_C_copy[n])):
        for k in range(len(tensor_C_copy[n][l])):
            for j in range(len(tensor_C_copy[n][l][k])):
                result[n] += tensor_C_copy[n][l][k][j][k][l][j]
print(result)
