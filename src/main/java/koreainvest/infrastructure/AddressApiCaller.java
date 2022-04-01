package koreainvest.infrastructure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Component
@Slf4j
public class AddressApiCaller {
    private final AddressApi addressApi;

    public AddressApiCaller(@Value("${api.address.base-url}") String baseUrl) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        log.info("baseUrl = {}", baseUrl);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        this.addressApi = retrofit.create(AddressApi.class);
    }

    public AddressApiDto.GetAddressInfo getAddressInfo(String roadName) {
        try {
            Call<AddressApiDto.GetAddressInfo> call = addressApi.getAddressInfo(roadName);
            System.out.println("도로명주소 = " + roadName + ", 주소 API 호출중...");
            var response = call.execute().body();
            if (response.getResult().getCommonResponse().getErrorMessage() == null) {
                throw new RuntimeException("response 응답값이 존재하지 않습니다.");
            }
            if (response != null) {
                return response;
            }

            throw new RuntimeException("response 응답이 올바르지 않습니다. header=" + response);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("response API error 발생! errorMessage=" + e.getMessage());
        }
    }
}
