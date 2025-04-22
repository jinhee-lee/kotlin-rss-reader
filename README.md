# 과제 진행 요구 사항
- 미션은 RSS 리더 저장소를 포크하고 클론하는 것으로 시작한다.
- 기능을 구현하기 전 README.md에 구현할 기능 목록을 정리해 추가한다.
- Git의 커밋 단위는 앞 단계에서 README.md에 정리한 기능 목록 단위로 추가한다.
- AngularJS Git Commit Message Conventions을 참고해 커밋 메시지를 작성한다.

# 기능 요구 사항
- 기술 블로그의 RSS를 모아 나만의 블로그 모음을 만들어 보자.
- 각 기술 블로그의 RSS를 가져와 최신 게시글 목록을 구성한다.
- 게시글은 작성 날짜 기준으로 내림차순 정렬하여 최대 10개까지 출력한다.
- 키워드를 입력하면 제목에 해당 단어가 포함된 게시글만 필터링하여 출력한다.

- # 실행 결과
```
검색어를 입력하세요 (없으면 전체 출력):

[1] 코루틴과 스레드의 차이점 (2024-04-19) - https://example.com/post2
[2] 안드로이드 UI 테스팅 전략 (2024-04-18) - https://example.com/post1

검색어를 입력하세요 (없으면 전체 출력):
코루틴

[1] 코루틴과 스레드의 차이점 (2024-04-19) - https://example.com/post2

```

# 힌트
```
val factory = DocumentBuilderFactory.newInstance()
val xml = factory.newDocumentBuilder()
.parse("https://techblog.woowahan.com/feed")
val channel = xml.getElementsByTagName("channel").item(0)
```


# 기술 블로그 
- https://woowabros.github.io/feed.xml
- https://toss.tech/rss.xml