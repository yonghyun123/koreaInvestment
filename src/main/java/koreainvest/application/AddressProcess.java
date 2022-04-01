package koreainvest.application;

import koreainvest.infrastructure.AddressApiCaller;
import koreainvest.infrastructure.AddressApiDto;
import koreainvest.utils.Console;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressProcess implements CommandLineRunner {

    //주소를 판단하는 주소 API https://www.juso.go.kr/addrlink/
    private final AddressApiCaller addressApiCaller;

    @Override
    public void run(String... args) throws Exception {
        //ex) input
        //성남, 분당 백 현 로 265, 푸른마을 아파트로 보내주세요!!
        //백현로
        //마포구 도화-2길 코끼리분식
        //도화2길
        System.out.println("주소를 입력하세요");
        String roughAddress = Console.readLine();

        String exceptAddress = exceptSpecial(roughAddress); //특수문자 제거

        String result = correctAddress(exceptAddress);
        System.out.println("result = " + result);

    }

    /**
     * 정규식을 이용한 특수문자, 공백 제거
     * @param roughAddress
     * @return
     */
    public String exceptSpecial(String roughAddress){
        String result = "";
        for(int i = 0; i < roughAddress.length(); i++){
            if(String.valueOf(roughAddress.charAt(i)).matches("[a-zA-Z0-9 ㄱ-ㅎㅏ-ㅣ가-힣]")){
                result += roughAddress.charAt(i);
            }
        }
        return result.replaceAll(" ","");
    }

    /**
     * 도로 정제 메서드 호출
     * @param address
     * @return
     */
    public String correctAddress(String address){

        //첫문자로 "로","길" 은 제외
        for(int i = 1; i < address.length(); i++){
            //"로" 에 대한 주소 검색
            String convertedAddress = findByRoad(address, i);
            if(convertedAddress != null) return convertedAddress;
        }

        for(int i = 1; i < address.length(); i++){
            //"길" 에 대한 주소 검색
            String convertedAddress = findByStreet(address, i);
            if(convertedAddress != null) return convertedAddress;
        }
        return "fail"; //주소를 찾지 못한 경우
    }

    /**
     * "로" 에 해당하는 주소 정제 API 호출
     * @param inputAddress
     * @param index
     * @return
     */
    public String findByRoad(String inputAddress, int index){
        String beforeResultRoad = null;
        if("로".equals(String.valueOf(inputAddress.charAt(index)))){
            String resultRoad = "";
            int strCnt = 0;
            for(int j = index; j >= 0; j--){
                resultRoad = inputAddress.charAt(j) + resultRoad;
                //도로명주소가 3글자 이상이면 판단 시작
                if(strCnt >= 2){
                    //API 호출을 통해 도로명주소를 가져옴
                    AddressApiDto.GetAddressInfo addressInfo = addressApiCaller.getAddressInfo(resultRoad);

                    //결과 건수가 존재할 때,
                    if(addressInfo.getResult().getCommonResponse().getTotalCount() > 0){
                        String getApiRoadName = addressInfo.getResult()
                                .getJusoResponses()
                                .get(0)
                                .getRoadName();

                        if(resultRoad.equals(getApiRoadName)) {
                            //호출한 도로명주소와 API 도로명 주소가 같으면 결과값 리턴
                            beforeResultRoad =  getApiRoadName;
                        }
                    }
                }
                strCnt += 1;
            }
        }
        return beforeResultRoad;
    }

    /**
     * "길" 에 해당하는 주소 정제 API 호출
     * @param inputAddress
     * @param index
     * @return
     */
    public String findByStreet(String inputAddress, int index){
        String beforeResultRoad = null;
        if("길".equals(String.valueOf(inputAddress.charAt(index)))){
            String resultRoad = "";
            int strCnt = 0; //글자수 판단
            for(int j = index; j >= 0; j--){
                resultRoad = inputAddress.charAt(j) + resultRoad;
                //도로명주소가 3글자 이상이면 판단 시작
                if(strCnt >= 2){
                    //API 호출을 통해 도로명주소를 가져옴
                    AddressApiDto.GetAddressInfo addressInfo = addressApiCaller.getAddressInfo(resultRoad);

                    //결과 건수가 존재할 때,
                    if(addressInfo.getResult().getCommonResponse().getTotalCount() > 0){
                        String getApiRoadName = addressInfo.getResult()
                                .getJusoResponses()
                                .get(0)
                                .getRoadName();

                        if(resultRoad.equals(getApiRoadName)) {
                            //호출한 도로명주소와 API 도로명 주소가 같으면 결과값 리턴
                            beforeResultRoad = getApiRoadName;
                        }
                    }
                }
                strCnt += 1;
            }
        }
        return beforeResultRoad;
    }
}