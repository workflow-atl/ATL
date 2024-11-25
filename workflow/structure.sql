select wf.flow_id,
       wf.name as workflow_name,
       wv.workflow_id,
       wfa_from.name as from_activity_name,
       wft.from_activity_id,
       wfa_to.name as to_activity_name,
       wft.to_activity_id
from APEX_240100.wwv_flow_workflows wf
inner join APEX_240100.wwv_flow_workflow_versions wv
       on wf.flow_id = wv.flow_id and wf.id = wv.workflow_id
inner join APEX_240100.wwv_flow_workflow_activities wfa_from
       on wv.flow_id = wfa_from.flow_id and wv.id = wfa_from.workflow_version_id 
inner join APEX_240100.wwv_flow_workflow_transitions wft
       on wfa_from.id = wft.from_activity_id
inner join APEX_240100.wwv_flow_workflow_activities wfa_to
       on wv.flow_id = wfa_to.flow_id and wv.id = wfa_to.workflow_version_id and wft.to_activity_id = wfa_to.id    
where wf.name like 'Doctor Appointment - corrected%';
