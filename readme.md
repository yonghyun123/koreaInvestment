###기술스택

 - IntelliJ

 - Retrofit2

 - OpenAPI (https://www.juso.go.kr)

 - OpenJDK 17.0.1

 

###실행방법

 - 프로젝트 경로: $ ./gradlew build

 - 빌드 완료후(경로를 build/lib에서) : ~build/lib $ java -jar address-0.0.1-SNAPSHOP

 

###구현방식

 - 입력받은 문자열에 대해 특수문자, 공백제거

 - "로", "길"에 해당하는 인덱스 탐색

 - 인덱스를 늘려가면서 임시주소 추출

 - 해당 임시주소를 API로 호출해 검증

 - 검증에 성공하면 도로명주소 return

 - 검증에 실패하면 "fail" return