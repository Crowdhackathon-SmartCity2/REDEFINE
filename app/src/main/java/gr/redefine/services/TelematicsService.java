package gr.redefine.services;

import java.util.List;

import gr.redefine.bean.TelematicsBean;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TelematicsService {
    @GET("/")
    Call<List<TelematicsBean>> getTimes();
}
