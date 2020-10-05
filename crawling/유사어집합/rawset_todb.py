import pymysql

conn = pymysql.connect(host = 'dbinstance.c4nf0uecennm.us-east-2.rds.amazonaws.com', user = 'weforvegan',
                       password = 'sungshin18', db = 'weforvegan', charset = 'utf8')
cursor = conn.cursor()

f = open("집합분류.txt", "r")
count = 1
vegantype = None

while True:
    line = f.readline()
    if not line: break
    sql = """insert into rawset(rs_idx, rs_set, rs_cannoteattype) values (%s, %s, %s)"""
    cursor.execute(sql, (count, line, vegantype))
    conn.commit()
    count = count + 1
conn.close()
f.close()
    
