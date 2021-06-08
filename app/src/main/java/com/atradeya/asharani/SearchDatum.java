
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("super_sub_cat_id")
    @Expose
    private String superSubCatId;
    @SerializedName("sub_categories")
    @Expose
    private String subCategories;
    @SerializedName("categories")
    @Expose
    private String categories;
    @SerializedName("searchcat")
    @Expose
    private String searchcat;
    @SerializedName("searchsubcat")
    @Expose
    private String searchsubcat;
    @SerializedName("searchsupercat")
    @Expose
    private String searchsupercat;
    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("Tsicode")
    @Expose
    private String tsicode;
    @SerializedName("UniqueCodeForImport")
    @Expose
    private String uniqueCodeForImport;
    @SerializedName("SKU")
    @Expose
    private String sKU;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("reviews")
    @Expose
    private String reviews;
    @SerializedName("specification")
    @Expose
    private String specification;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("inc_vat")
    @Expose
    private String incVat;
    @SerializedName("ex_vat")
    @Expose
    private String exVat;
    @SerializedName("vat_deductable")
    @Expose
    private String vatDeductable;
    @SerializedName("publish_status")
    @Expose
    private String publishStatus;
    @SerializedName("tax_class")
    @Expose
    private String taxClass;
    @SerializedName("products_images")
    @Expose
    private String productsImages;
    @SerializedName("product_image_two")
    @Expose
    private String productImageTwo;
    @SerializedName("pdf_manual")
    @Expose
    private String pdfManual;
    @SerializedName("pdf_manual2")
    @Expose
    private String pdfManual2;
    @SerializedName("pdf_manual3")
    @Expose
    private String pdfManual3;
    @SerializedName("pdf_manual4")
    @Expose
    private String pdfManual4;
    @SerializedName("pdf_manual5")
    @Expose
    private String pdfManual5;
    @SerializedName("pdf_manual6")
    @Expose
    private String pdfManual6;
    @SerializedName("pdf_name")
    @Expose
    private String pdfName;
    @SerializedName("pdf2_name")
    @Expose
    private String pdf2Name;
    @SerializedName("pdf3_name")
    @Expose
    private String pdf3Name;
    @SerializedName("pdf4_name")
    @Expose
    private String pdf4Name;
    @SerializedName("pdf5_name")
    @Expose
    private String pdf5Name;
    @SerializedName("pdf6_name")
    @Expose
    private String pdf6Name;
    @SerializedName("supplier_with_cost")
    @Expose
    private String supplierWithCost;
    @SerializedName("dateandtime")
    @Expose
    private String dateandtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuperSubCatId() {
        return superSubCatId;
    }

    public void setSuperSubCatId(String superSubCatId) {
        this.superSubCatId = superSubCatId;
    }

    public String getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(String subCategories) {
        this.subCategories = subCategories;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getSearchcat() {
        return searchcat;
    }

    public void setSearchcat(String searchcat) {
        this.searchcat = searchcat;
    }

    public String getSearchsubcat() {
        return searchsubcat;
    }

    public void setSearchsubcat(String searchsubcat) {
        this.searchsubcat = searchsubcat;
    }

    public String getSearchsupercat() {
        return searchsupercat;
    }

    public void setSearchsupercat(String searchsupercat) {
        this.searchsupercat = searchsupercat;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTsicode() {
        return tsicode;
    }

    public void setTsicode(String tsicode) {
        this.tsicode = tsicode;
    }

    public String getUniqueCodeForImport() {
        return uniqueCodeForImport;
    }

    public void setUniqueCodeForImport(String uniqueCodeForImport) {
        this.uniqueCodeForImport = uniqueCodeForImport;
    }

    public String getSKU() {
        return sKU;
    }

    public void setSKU(String sKU) {
        this.sKU = sKU;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIncVat() {
        return incVat;
    }

    public void setIncVat(String incVat) {
        this.incVat = incVat;
    }

    public String getExVat() {
        return exVat;
    }

    public void setExVat(String exVat) {
        this.exVat = exVat;
    }

    public String getVatDeductable() {
        return vatDeductable;
    }

    public void setVatDeductable(String vatDeductable) {
        this.vatDeductable = vatDeductable;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public String getProductsImages() {
        return productsImages;
    }

    public void setProductsImages(String productsImages) {
        this.productsImages = productsImages;
    }

    public String getProductImageTwo() {
        return productImageTwo;
    }

    public void setProductImageTwo(String productImageTwo) {
        this.productImageTwo = productImageTwo;
    }

    public String getPdfManual() {
        return pdfManual;
    }

    public void setPdfManual(String pdfManual) {
        this.pdfManual = pdfManual;
    }

    public String getPdfManual2() {
        return pdfManual2;
    }

    public void setPdfManual2(String pdfManual2) {
        this.pdfManual2 = pdfManual2;
    }

    public String getPdfManual3() {
        return pdfManual3;
    }

    public void setPdfManual3(String pdfManual3) {
        this.pdfManual3 = pdfManual3;
    }

    public String getPdfManual4() {
        return pdfManual4;
    }

    public void setPdfManual4(String pdfManual4) {
        this.pdfManual4 = pdfManual4;
    }

    public String getPdfManual5() {
        return pdfManual5;
    }

    public void setPdfManual5(String pdfManual5) {
        this.pdfManual5 = pdfManual5;
    }

    public String getPdfManual6() {
        return pdfManual6;
    }

    public void setPdfManual6(String pdfManual6) {
        this.pdfManual6 = pdfManual6;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdf2Name() {
        return pdf2Name;
    }

    public void setPdf2Name(String pdf2Name) {
        this.pdf2Name = pdf2Name;
    }

    public String getPdf3Name() {
        return pdf3Name;
    }

    public void setPdf3Name(String pdf3Name) {
        this.pdf3Name = pdf3Name;
    }

    public String getPdf4Name() {
        return pdf4Name;
    }

    public void setPdf4Name(String pdf4Name) {
        this.pdf4Name = pdf4Name;
    }

    public String getPdf5Name() {
        return pdf5Name;
    }

    public void setPdf5Name(String pdf5Name) {
        this.pdf5Name = pdf5Name;
    }

    public String getPdf6Name() {
        return pdf6Name;
    }

    public void setPdf6Name(String pdf6Name) {
        this.pdf6Name = pdf6Name;
    }

    public String getSupplierWithCost() {
        return supplierWithCost;
    }

    public void setSupplierWithCost(String supplierWithCost) {
        this.supplierWithCost = supplierWithCost;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

}
