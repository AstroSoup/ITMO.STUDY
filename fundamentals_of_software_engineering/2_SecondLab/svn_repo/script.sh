#!/bin/bash

REPO_URL="file:///home/studs/s465592/fundamentals_of_software_engineering/lab2/svn_repo"
WC="wc"

USER_1="Serge Klimenkov <Serge.Klimenkov@cs.ifmo.ru>"
USER_2="Linus Torvalds <torvalds@linux-foundation.org>"


sync_wc_to_dir() {
    TARGET_DIR=$1

    rm -rf *
    cp -r "$TARGET_DIR"/. .

    svn status | while read status file; do
        case "$status" in
            \!)
                svn rm "$file" --force
                ;;
            \?)
                svn add "$file"
                ;;
        esac
    done
}

svn mkdir \
  $REPO_URL/trunk \
  $REPO_URL/branches \
  $REPO_URL/tags \
  -m "Init repo."

svn checkout $REPO_URL/trunk $WC
cd $WC

# r0
sync_wc_to_dir ../../commits/commit0
svn commit -m "r0" --username "$USER_1"

# r1
svn copy $REPO_URL/trunk $REPO_URL/branches/slave_1 -m "create slave_1" --username "$USER_1"

svn switch $REPO_URL/branches/slave_1
sync_wc_to_dir ../../commits/commit1
svn commit -m "r1" --username "$USER_2"

# r2
svn switch $REPO_URL/trunk
sync_wc_to_dir ../../commits/commit2
svn commit -m "r2" --username "$USER_1"

# r3
svn copy $REPO_URL/trunk $REPO_URL/branches/slave_2 -m "create slave_2" --username "$USER_1"

svn switch $REPO_URL/branches/slave_2
sync_wc_to_dir ../../commits/commit3
svn commit -m "r3" --username "$USER_1"

# r4
svn switch $REPO_URL/trunk
sync_wc_to_dir ../../commits/commit4
svn commit -m "r4" --username "$USER_1"

# r5
sync_wc_to_dir ../../commits/commit5
svn commit -m "r5" --username "$USER_1"

# r6
svn switch $REPO_URL/branches/slave_1
sync_wc_to_dir ../../commits/commit6
svn commit -m "r6" --username "$USER_2"

# r7
svn switch $REPO_URL/branches/slave_2
sync_wc_to_dir ../../commits/commit7
svn commit -m "r7" --username "$USER_1"

# r8
svn switch $REPO_URL/branches/slave_1
svn merge $REPO_URL/branches/slave_2 --accept postpone

sync_wc_to_dir ../../commits/commit8
svn commit -m "r8" --username "$USER_2"

svn delete $REPO_URL/branches/slave_2 -m "delete slave_2" --username "$USER_2"

# r9
sync_wc_to_dir ../../commits/commit9
svn commit -m "r9" --username "$USER_2"

# r10
sync_wc_to_dir ../../commits/commit10
svn commit -m "r10" --username "$USER_2"

# r11
sync_wc_to_dir ../../commits/commit11
svn commit -m "r11" --username "$USER_2"

# r12
svn switch $REPO_URL/trunk
svn merge $REPO_URL/branches/slave_1 --accept postpone

sync_wc_to_dir ../../commits/commit12
svn commit -m "r12" --username "$USER_1"

svn delete $REPO_URL/branches/slave_1 -m "delete slave_1" --username "$USER_1"

# r13
svn copy $REPO_URL/trunk $REPO_URL/branches/slave_3 -m "create slave_3" --username "$USER_1"

svn switch $REPO_URL/branches/slave_3
sync_wc_to_dir ../../commits/commit13
svn commit -m "r13" --username "$USER_2"

# r14
sync_wc_to_dir ../../commits/commit14
svn commit -m "r14" --username "$USER_2"

# r15
svn switch $REPO_URL/trunk
svn merge $REPO_URL/branches/slave_3 --accept postpone

sync_wc_to_dir ../../commits/commit15
svn commit -m "r15" --username "$USER_1"

svn delete $REPO_URL/branches/slave_3 -m "delete slave_3" --username "$USER_1"

# r16
sync_wc_to_dir ../../commits/commit16
svn commit -m "r16" --username "$USER_1"