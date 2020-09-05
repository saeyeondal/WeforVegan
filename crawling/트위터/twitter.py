import tweepy

# 트위터 앱의 Keys and Access Tokens 탭 참조(자신의 설정 값을 넣어준다)
consumer_key =  ''
consumer_secret = ''

# 1. 인증요청(1차) : 개인 앱 정보 
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)

access_token = ''
access_token_secret= ''

# 2. access 토큰 요청(2차) - 인증요청 참조변수 이용
auth.set_access_token(access_token, access_token_secret)

# 3. twitter API 생성  
api = tweepy.API(auth)


keyword = "비건";     # 검색할 키워드

tweets = api.search(keyword) # 한 번에 15 트윗 검색 정보를 가져옴
# tweets = api.search(keyword, count=100) # 일반 계정으로는 최대 100개 최신 게시물을 가져올 수 있음
for num, tweet in enumerate(tweets):
    print(tweet.text)
    print (tweet.favorite_count)
    print (tweet.retweet_count)