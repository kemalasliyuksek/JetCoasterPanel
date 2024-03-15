class Ziyaretci {

    // Ziyaretçinin ismi
    private String isim;

    // Ziyaretçinin trene binip binmediğini gösteren flag
    private boolean bindiMi;

    public Ziyaretci(String isim) {
        this.isim = isim;
        this.bindiMi = false;
    }

    public String getIsim() {
        return isim;
    }

    public boolean isBindiMi() {
        return bindiMi;
    }

    public void setBindiMi(boolean bindiMi) {
        this.bindiMi = bindiMi;
    }

}