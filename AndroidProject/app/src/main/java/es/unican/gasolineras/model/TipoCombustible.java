package es.unican.gasolineras.model;

public enum TipoCombustible {


        GASOLINA_95_E5("1"),
        GASOLINA_95_E10("23"),
        GASOLINA_95_E5_PREMIUM("20"),
        GASOLINA_98_E5("3"),
        GASOLINA_98_E10("21"),
        GASOLEO_A_HABITUAL("4"),
        GASOLEO_PREMIUM("5"),
        GASOLEO_B("6"),
        GASOLEO_C("7"),
        BIOETANOL("16"),
        BIODIESEL("8"),
        GASES_LICUADOS_DEL_PETROLEO("17"),
        GAS_NATURAL_COMPRIMIDO("18"),
        GAS_NATURAL_LICUADO("19"),
        HIDROGENO("22"),
        FUELOLEO_BAJO_INDICE_AZUFRE("9"),
        FUELOLEO_ESPECIAL("10"),
        GASOLEO_PARA_USO_MARITIMO("11"),
        GASOLINA_DE_AVIACION("12"),
        QUEROSENO_DE_AVIACION_JET_A1("13"),
        QUEROSENO_DE_AVIACION_JET_A2("14");

        public final String id;

        private TipoCombustible(String id) {
            this.id = id;
        }


}
