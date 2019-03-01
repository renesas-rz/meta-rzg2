SOC="${@d.getVar('SOC_FAMILY').split(':')[1]}"
CAS="${@'1' if 'cas' in '${MACHINE_FEATURES}' else '0'}"

# USE_CAS for RZG2M (SoC: r8a774a1)
USE_CAS="${@'1' if '${CAS}' == '1' and ('${SOC}' == 'r8a774a1') else '0'}"

# Trap incorrect CAS setting for RZG2E
WARN="${@'1' if '${CAS}' == '1' and '${USE_CAS}' == '0' else '0'}"

# This function is to show a warning message when enabling CAS feature on RZG2E
python do_cas_checking() {
    warn=d.getVar('WARN')
    if warn == "1" :
        bb.warn("CAS only supports RZG2M (SoC: r8a774a1), this feature will be disabled automatically")
}

addtask cas_checking before do_configure after do_patch
