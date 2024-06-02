package com.example.gongmobile.api.service;

import com.example.gongmobile.api.model.BranchDetails;
import com.example.gongmobile.api.model.ListedBranch;
import com.example.gongmobile.api.model.ListedCoupon;
import com.example.gongmobile.api.model.PromoCode;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GongClient {
    @GET("branches")
    Call<List<ListedBranch>> listBranches(@Query("categoryId") String categoryId);

    @GET("branches/{branchId}")
    Call<BranchDetails> fetchBranchDetails(@Path("branchId") int branchId);

    @GET("branches/{branchId}/coupons")
    Call<List<ListedCoupon>> listCoupons(@Path("branchId") int branchId);

    @POST("branches/{branchId}/coupons/{couponId}/codes/generate")
    Call<PromoCode> generatePromoCode(@Path("branchId") int branchId, @Path("couponId") int couponId);
}
