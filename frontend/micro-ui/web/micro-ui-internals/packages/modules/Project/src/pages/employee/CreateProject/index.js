import { Loader } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { createProjectConfigMUKTA } from "../../../configs/createProjectConfigMUKTA";
import CreateProjectForm from "./CreateProjectForm";

const createFormDataForModifyProject = (data) => {
  return {
    "basicDetails_projectName": data?.basicDetails?.projectName,
    "basicDetails_projectDesc": data?.basicDetails?.projectDesc,
    "noSubProject_letterRefNoOrReqNo": "",
    "noSubProject_typeOfProject": {
        "id": 1,
        "name": "COMMON_MASTERS_MP_CWS",
        "code": "MP-CWS",
        "group": "Capital Works",
        "beneficiary": "Slum",
        "active": true,
        "projectSubType": [
            "MP001",
            "MP002"
        ]
    },
    "noSubProject_targetDemography": {
        "code": "SC",
        "name": "COMMON_MASTERS_SC",
        "description": "Area inhabited by SC",
        "effectiveFrom": 1676875575195,
        "effectiveTo": null,
        "active": true
    },
    "noSubProject_estimatedCostInRs": "2417",
    "noSubProject_geoLocation": "sdfdsf",
    "noSubProject_ulb": {
        "code": "pg.citya",
        "name": "City A",
        "i18nKey": "TENANT_TENANTS_PG_CITYA"
    },
    "noSubProject_ward": {
        "code": "B1",
        "name": "Ward 1",
        "i18nKey": "PG_CITYA_ADMIN_B1"
    },
    "noSubProject_locality": {
        "code": "SUN01",
        "name": "Ajit Nagar - Area1",
        "i18nKey": "PG_CITYA_ADMIN_SUN01",
        "label": "Locality"
    },
    "noSubProject_docs": {
        "noSubProject_doc_others_name": "File",
        "noSubProject_doc_project_proposal": [
            [
                "consumer-PB-CH-2022-07-27-001010.pdf",
                {
                    "file": {},
                    "fileStoreId": {
                        "fileStoreId": "96788909-8a43-4dcf-9cf8-5a182bef19aa",
                        "tenantId": "pg.citya"
                    }
                }
            ]
        ],
        "noSubProject_doc_finalized_worklist": [
            [
                "consumer-PB-CH-2022-07-27-001010.pdf",
                {
                    "file": {},
                    "fileStoreId": {
                        "fileStoreId": "80f439d2-d993-410c-afa7-a1d013d85911",
                        "tenantId": "pg.citya"
                    }
                }
            ]
        ],
        "noSubProject_doc_feasibility_analysis": [
            [
                "consumer-PB-CH-2022-07-27-001010.pdf",
                {
                    "file": {},
                    "fileStoreId": {
                        "fileStoreId": "60737627-f601-4332-94f4-e18bd5ffa164",
                        "tenantId": "pg.citya"
                    }
                }
            ]
        ],
        "noSubProject_doc_others": [
            [
                "consumer-PB-CH-2022-07-27-001010.pdf",
                {
                    "file": {},
                    "fileStoreId": {
                        "fileStoreId": "dcec0ff3-bbeb-4739-9d4f-114ae11d21af",
                        "tenantId": "pg.citya"
                    }
                }
            ]
        ]
    }
  }
}

const updateDefaultValuesForCreateProject = (setSessionFormData, sessionFormData, findCurrentDate, configs, ULBOptions) => {
        
        //remove all other default values other than validDefaultValues in Create flow.
        //This is required when navigating from Modify Project flow to Create Project flow, as the configs will persist the default values of modify
        let validDefaultValues = ["basicDetails_dateOfProposal", "noSubProject_ulb"];
        configs.defaultValues = Object.keys(configs?.defaultValues)
                               .filter(key=> validDefaultValues.includes(key))
                               .reduce((obj, key) => Object.assign(obj, {
                                [key] : configs.defaultValues[key]
                               }), {});
        //This will be done only sessionStorage is set to default
        if(!sessionFormData?.basicDetails_dateOfProposal || !sessionFormData.noSubProject_ulb) {
            configs.defaultValues.basicDetails_dateOfProposal = findCurrentDate();
            configs.defaultValues.noSubProject_ulb = ULBOptions[0];
            setSessionFormData({...configs?.defaultValues});
        }
        return configs;
}


const updateDefaultValuesForModifyProject =(setSessionFormData, sessionFormData, findCurrentDate, configs, ULBOptions, data) => {
        
        //This will be done only sessionStorage is set to default
        if((!sessionFormData?.basicDetails_dateOfProposal || !sessionFormData.noSubProject_ulb)){
            configs.defaultValues.basicDetails_dateOfProposal = findCurrentDate();
            configs.defaultValues.noSubProject_ulb = ULBOptions[0];
            configs.defaultValues.basicDetails_projectID = data?.basicDetails_projectID ? data?.basicDetails_projectID : "DEFAULT_ID";
            configs.defaultValues.basicDetails_projectName = data?.basicDetails_projectName ? data?.basicDetails_projectName : "DEFAULT_NAME";
            configs.defaultValues.basicDetails_projectDesc = data?.basicDetails_projectDesc ? data?.basicDetails_projectDesc : "DEFAULT_DESC";
            setSessionFormData({...configs?.defaultValues});
        }
        return configs;
}

const CreateProject = () => {

    const {t} = useTranslation();
    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);
    const ULB = Digit.Utils.locale.getCityLocale(tenantId);
    const [isFormReady, setIsFormReady] = useState(false);
    let ULBOptions = [];
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB });
    const queryStrings = Digit.Hooks.useQueryParams();
    const [modifiedProjectFormData, setModifiedProjectFormData] = useState([]);
    const isModify = queryStrings?.isEdit === "true";
    const modifySearchParams = {
      Projects : [
          {
              tenantId : queryStrings?.tenantId,
              projectNumber : queryStrings?.projectNumber
          }
      ]
    } 
    const modifyFilters = {
        limit : 1,
        offset : 0,
        includeAncestors : true,
        includeDescendants : true
    }

    const { isLoading : isProjectDetailsLoading, data : ProjectDetails } = Digit.Hooks.works.useViewProjectDetails(t, tenantId, modifySearchParams, modifyFilters, headerLocale);

    //TODO:
    // if(!isProjectDetailsLoading) {
    //     setModifiedProjectFormData(createFormDataForModifyProject(ProjectDetails?.projectDetails?.searchedProject));
    // }

    const findCurrentDate = () => {
      var date = new Date();
      var dateString = new Date(date.getTime() - (date.getTimezoneOffset() * 60000 )).toISOString().split("T")[0];
      return dateString;
    } 

    //TODO:
    // const { isLoading, data : configs} = Digit.Hooks.useCustomMDMS( //change to data
    //   stateTenant,
    //   Digit.Utils.getConfigModuleName(),
    //   [
    //       {
    //           "name": "CreateProjectConfig"
    //       }
    //   ],
    //   {
    //     select: (data) => {
    //         return data?.[Digit.Utils.getConfigModuleName()]?.CreateProjectConfig[0];
    //     },
    //   }
    // );

    let configs = createProjectConfigMUKTA?.CreateProjectConfig[0];

    //create a session with config based default values (not pre-populated)
    const projectSession = Digit.Hooks.useSessionStorage("NEW_PROJECT_CREATE", 
      configs?.defaultValues
    );

    const [sessionFormData, setSessionFormData, clearSessionFormData] = projectSession;

    useEffect(()=>{
      if(configs) {
        if(!isModify) {
             //if Create Projects Flow - 
            configs = updateDefaultValuesForCreateProject(setSessionFormData, sessionFormData, findCurrentDate, configs, ULBOptions);
        }else{
            //if Modify Projects Flow - 
            configs = updateDefaultValuesForModifyProject(setSessionFormData, sessionFormData, findCurrentDate, configs, ULBOptions, []);
        }
        setIsFormReady(true);
    }
    },[configs]);

    // if(isLoading) return <Loader />
    return (
      <React.Fragment>
        {isFormReady && 
          <CreateProjectForm t={t} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData} createProjectConfig={configs} isModify={isModify}></CreateProjectForm>
        } 
        </React.Fragment>
    )
}

export default CreateProject;