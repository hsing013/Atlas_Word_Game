import sys



def checkIfExists(wordsToFind, list, left, right):
    if (len(list) == 0):
        return False
    if (left == right):
        potentialWord = list[left]
        for i in wordsToFind:
            if i == potentialWord:
                return True
        return False
    else:
        m = (left + right) // 2
        return checkIfExists(wordsToFind, list, left, m) or checkIfExists(wordsToFind, list, m+1, right)


fileR = open("words_alpha.txt")

fileW = open("modifiedWords.txt", 'w')






s = fileR.readlines()

s = [x.strip() for x in s]



fileR.close()

modified = set()

count = 0

for i in s:
    count = count + 1
    sub1 = None
    sub2 = None
    sub3 = None
    if i.endswith('s'):
        sub1 = i[:-1]
        if sub1 in modified:
            continue
    if i.endswith("ed"):
        sub2 = i[:-1]
        if sub2 in modified:
            continue
    if i.endswith("ed"):
        sub2 = i[:-2]
        if sub2 in modified:
            continue
    if i.endswith("ing"):
        sub3 = i[:-3]
        if sub3 in  modified:
            continue

    modified.add(i)
    print(count)

myList = list(modified)

myList = sorted(myList)

for i in myList:
    fileW.write(i + '\n')

fileW.close()

    
