package com.eztexting.api.client.api.toolbox.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Carrier names
 */
@SuppressWarnings("SpellCheckingInspection")
public enum CarrierName {
    // Carrier Codes (United States)
    ACSUS("ACS Wireless", "ACSUS"),
    ALLTELUS("Alltel", "ALLTELUS"),
    ALLWESTUS("RINA/All West Wireless", "ALLWESTUS"),
    APPALACHIANUS("EKN/Appalachian Wireless", "APPALACHIANUS"),
    ARCHWIRELESSUS("Arch Wireless", "ARCHWIRELESSUS"),
    ATTUS("AT&T Wireless", "ATTUS"),
    BLUEGRASSUS("Bluegrass Cellular", "BLUEGRASSUS"),
    BOOSTUS("Boost USA", "BOOSTUS"),
    CELLCOMUS("Cellcom", "CELLCOMUS"),
    CELLULARSOUTHUS("Cellular South", "CELLULARSOUTHUS"),
    CENTENNIALUS("Centennial", "CENTENNIALUS"),
    CENTRALUS("Central Wireless", "CENTRALUS"),
    CHOICEUS("Amerilink", "CHOICEUS"),
    CINBELLUS("Cincinnati Bell", "CINBELLUS"),
    CINGULARUS("AT&T (Formerly Cingular Wireless)", "CINGULARUS"),
    COXUS("Cox Communications", "COXUS"),
    CRICKETUS("Cricket Communications", "CRICKETUS"),
    CTCUS("RINA/CTC Telecom-Cambridge", "CTCUS"),
    DOBSONUS("Dobson", "DOBSONUS"),
    EAGLEUS("RINA/Snake River PCS", "EAGLEUS"),
    ECITUS("ECIT - Cell One of East Central IL", "ECITUS"),
    EDGEUS("Edge Wireless", "EDGEUS"),
    ELEMENTUS("Element Mobile", "ELEMENTUS"),
    FARMERSMUTUALUS("RINA/FMTC-Farmers Mutual Telephone Co.", "FARMERSMUTUALUS"),
    GENERALCOMUS("GCI Communications", "GENERALCOMUS"),
    GOLDSTARUS("RINA/Silverstar", "GOLDSTARUS"),
    IMMIXUS("Immix Wireless/PC Management", "IMMIXUS"),
    INLANDUS("Inland Cellular", "INLANDUS"),
    IOWAWIRELESSUS("Iowa Wireless", "IOWAWIRELESSUS"),
    IVCUS("Illinois Valley Cellular", "IVCUS"),
    METROCALLUS("Metrocall Wireless", "METROCALLUS"),
    METROPCSUS("Metro PCS", "METROPCSUS"),
    MIDWESTUS("Midwest Wireless", "MIDWESTUS"),
    NEXTECHUS("Nex-Tech Wireless", "NEXTECHUS"),
    NORTHCOASTUS("North Coast PCS", "NORTHCOASTUS"),
    NTELOSUS("nTelos", "NTELOSUS"),
    NUCLANATURITAUS("RINA/Nucla-Naturita Telephone Co.", "NUCLANATURITAUS"),
    PACBELLUS("Pacific Bell", "PACBELLUS"),
    PLATEAUUS("Plateau Telecom", "PLATEAUUS"),
    POCKETUS("Pocket Wireless", "POCKETUS"),
    REVOLUS("Revol", "REVOLUS"),
    RURALCELUS("RCC/Unicel", "RURALCELUS"),
    SOUTHCENTRALUTAHUS("RINA/South Central", "SOUTHCENTRALUTAHUS"),
    SOUTHCANAANUS("South Canaan Cell", "SOUTHCANAANUS"),
    SOUTHWESTBELLUS("Southern Bell", "SOUTHWESTBELLUS"),
    SPRINTUS("Sprint PCS", "SPRINTUS"),
    SUNCOMUS("Suncom", "SUNCOMUS"),
    SYRINGAUS("RINA/Syringa Wireless", "SYRINGAUS"),
    THUMBUS("Thumb Cellular", "THUMBUS"),
    TMOBILEUS("T-Mobile USA", "TMOBILEUS"),
    TRITONPCSUS("Triton PCS", "TRITONPCSUS"),
    UNITAHBASINUS("RINA/UBET", "UNITAHBASINUS"),
    UNITEDWIRELESSUS("United Wireless", "UNITEDWIRELESSUS"),
    USCELLULARUS("US Cellular", "USCELLULARUS"),
    VERIZONUS("Verizon Wireless", "VERIZONUS"),
    VIAEROUS("Viaero Wireless", "VIAEROUS"),
    VIRGINUS("Virgin USA", "VIRGINUS"),
    WCENTRALUS("West Central Wireless", "WCENTRALUS"),
    WESTERNWUS("Western Wireless", "WESTERNWUS"),

    // Carrier Codes (Canada)
    FIDOCA("Fido", "FIDOCA"),
    BELLCA("Bell Mobility", "BELLCA"),
    WINDCA("Wind Mobile", "WINDCA"),
    TELUSCA("Telus", "TELUSCA"),
    SASKTELCA("SaskTel", "SASKTELCA"),
    VIRGINCA("Virgin Canada", "VIRGINCA"),
    MTSCA("MTS Canada", "MTSCA"),
    ROGERSCA("Rogers", "ROGERSCA"),
    VIDEOTRONCA("Videotron", "VIDEOTRONCA");

    private String name;
    private String code;

    CarrierName(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @JsonCreator
    public static CarrierName fromValue(String code) {
        for (CarrierName n : values()) {
            if (Objects.equals(n.code, code)) {
                return n;
            }
        }
        throw new IllegalArgumentException("there is no type for CarrierName: " + code);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @JsonValue
    @Override
    public String toString() {
        return code;
    }
}
