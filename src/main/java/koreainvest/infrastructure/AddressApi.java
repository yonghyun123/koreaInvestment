package koreainvest.infrastructure;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddressApi {
    String serviceKey = "devU01TX0FVVEgyMDIyMDMyOTIxNTExMjExMjQwMjI=";

    @GET("addrLinkApi.do?currentPage=1&countPerPage=10&confmKey="+serviceKey+"&resultType=json")
    Call<AddressApiDto.GetAddressInfo> getAddressInfo(@Query("keyword") String roadName);
}
