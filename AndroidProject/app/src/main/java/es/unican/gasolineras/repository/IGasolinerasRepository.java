package es.unican.gasolineras.repository;

/**
 * A repository to retrieve gas stations
 */
public interface IGasolinerasRepository {

        /**
         * Asynchronously requests a list of gas stations.
         * @param cb the callback that will asynchronously process the returned gas stations
         * @param ccaa id of the "comunidad autonoma", as defined in IDCCAAs
         */
        public void requestGasolineras(ICallBack cb, String ccaa);

        /**
         * Asynchronously requests a list of gas stations which sell a specific fuel.
         * @param cb the callback that will asynchronously process the returned gas stations
         * @param ccaa id of the "comunidad autonoma", as defined in IDCCAAs
         * @param combustible id of "producto petrolifero", as defined in TipoCombustible
         */
        public void requestGasolinerasCombustible(ICallBack cb, String ccaa, String combustible);
}
