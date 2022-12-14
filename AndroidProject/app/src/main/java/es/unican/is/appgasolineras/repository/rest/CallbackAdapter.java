package es.unican.is.appgasolineras.repository.rest;

import java.util.List;

import es.unican.is.appgasolineras.common.Callback;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.model.GasolinerasResponse;
import retrofit2.Call;
import retrofit2.Response;

/**
 * This class adapts a Retrofit Callback into our own Callback class
 * @param <T>
 */
class CallbackAdapter<T> implements retrofit2.Callback<T> {
    private final Callback<T> cb;

    public CallbackAdapter(Callback<T> cb) {
        this.cb = cb;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        cb.onSuccess(response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        cb.onFailure();
    }
}