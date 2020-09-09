import re


g = open('db_raw.txt', 'r', encoding='UTF8')
cList = open('나라.txt', 'r', encoding='UTF8').read().split('\n')


def deleteToken(raw, rawSet):

    findc = []

    for j in range(len(cList)):
        p = re.compile(
            f'(\({cList[j]}산?\))|' + f'(\({cList[j]}산?/.*?\))|' + f'(\(.*?/{cList[j]}산?\))|' + f'(\(.*?/{cList[j]}산?/.*?\))')
        print(cList[j])
        if cList[j] in raw:
            q = re.sub(p, "", raw)
            findc.append(q)
            raw = q
        else:
            findc.append(0)
            continue

    print(findc[-1])
    rawSet.add(findc[-1])
    return rawSet


rawSet = set()

while True:
    wordList = g.readline()
    if not wordList:
        break
    deleteToken(wordList, rawSet)


g.close()

g = open("원성분.txt", "w")
for i in rawSet:
    g.write(i+'\n')
g.close()
