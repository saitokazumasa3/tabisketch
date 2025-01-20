DO
$$
    BEGIN
        IF NOT EXISTS(SELECT *
                  FROM PG_TYPE TYPE
                           JOIN PG_ENUM ENUM ON TYPE.OID = ENUM.ENUMTYPID
                  WHERE TYPE.TYPNAME = 'transportations')
        THEN
            CREATE TYPE transportations AS ENUM (
                'DRIVING',
                'BICYCLING',
                'TRANSIT',
                'WALKING'
                );
        END IF;
    END
$$;
